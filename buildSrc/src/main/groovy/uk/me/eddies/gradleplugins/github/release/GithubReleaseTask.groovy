// Original version from Riiid! (https://github.com/riiid) and Anvith Bhat (https://github.com/humblerookie), used under Apache licence 2.0

package uk.me.eddies.gradleplugins.github.release

import groovyx.net.http.ContentType
import groovyx.net.http.Method
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.Internal

class GithubReleaseTask extends DefaultTask {

    static final String HEADER_USER_AGENT = 'gradle-github-plugin'

    @TaskAction
    public void release() {
        def baseUrl = project.githubRelease.getBaseUrl()
        def accept = project.githubRelease.getAcceptHeader()
        
        def http = new JsonHttpBuilder(baseUrl)

        def path = "/repos/" +
                "${project.githubRelease.owner}/" +
                "${project.githubRelease.repo}/releases"

        def postBody = [
                tag_name        : project.githubRelease.getTagName(),
                target_commitish: findGitRevision(),
                name            : project.githubRelease.getName(),
                body            : project.githubRelease.getBody(),
                prerelease      : project.githubRelease.isPrerelease(),
                draft           : project.githubRelease.isDraft()
        ]

        http.request(Method.POST) {
            uri.path += path
            requestContentType = ContentType.JSON
            body = postBody

            headers.'User-Agent' = HEADER_USER_AGENT
            headers.'Authorization' = "token ${project.githubRelease.token}"
            headers.'Accept' = accept

            def postLogMessage = "POST ${uri.path}\n" +
                " > User-Agent: ${headers['User-Agent']}\n" +
                " > Authorization: (not shown)\n" +
                " > Accept: ${headers.Accept}\n" +
                " > body: $body\n"
            logger.debug "$postLogMessage"

            response.success = { resp, json ->
                logger.debug "< $resp.statusLine"
                logger.debug 'Response headers: \n' + resp.headers.collect { "< $it" }.join('\n')
                if (project.githubRelease.assets != null) {
                    postAssets(json.upload_url, project.githubRelease.assets, accept)
                }
            }

            response.failure = { resp, json ->
                logger.error "Error in $postLogMessage"
                logger.debug 'Response headers: \n' + resp.headers.collect { "< $it" }.join('\n')
                def errorMessage = json?json.message:resp.statusLine
                def ref = json?"See $json.documentation_url":''
                def errorDetails = json && json.errors? "Details: " + json.errors.collect { it }.join('\n'):''
                throw new GradleScriptException("$errorMessage. $ref. $errorDetails", null)
            }
        }
    }

    void postAssets(uploadUrl, assets, accept) {
        assets.each { asset ->
            def file = new File(asset as String)
            def name = asset.split('/')[-1]

            def upload = uploadUrl.replace(
                    '{?name,label}', "?name=${name}&label=${name}")
            logger.debug "upload url: ${upload}"

            def url = new URL(upload as String)
            def host = url.host + (url.port > 0 ? ":" + url.port + "" : "")
            host = "${url.protocol}://${host}"
            def path = url.path
            def http = new JsonHttpBuilder(host)

            if (file.exists()) {

                def map = URLConnection.getFileNameMap()
                def contentType = map.getContentTypeFor(file.absolutePath)

                http.request(Method.POST) { req ->
                    uri.path = path
                    uri.query = [
                            name: name,
                    ]
                    send ContentType.BINARY, file.bytes

                    headers.'User-Agent' = HEADER_USER_AGENT
                    headers.'Authorization' = "token ${project.githubRelease.token}"
                    headers.'Accept' = accept
                    headers.'Content-Type' = contentType

                    response.success = { resp, json ->
                        logger.debug "$json"
                    }

                    response.failure = { resp, json ->
                        logger.error "$json"
                        throw new GradleScriptException("Failed to upload asset. Details: $json", null)
                    }
                }
            } else {
                throw new GradleScriptException("Failed to upload asset $file as it does not exist.", null)
            }
        }
    }
    
    String findGitRevision() {
        def sout = new StringBuilder(), serr = new StringBuilder()
        def process = 'git rev-parse HEAD'.execute()
        process.consumeProcessOutput(sout, serr)
        process.waitForOrKill(1000)
        return sout.toString().trim()
    }
}

