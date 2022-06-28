// Original version from Riiid! (https://github.com/riiid) and Anvith Bhat (https://github.com/humblerookie), used under Apache licence 2.0

package uk.me.eddies.gradleplugins.github.release

import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ParserRegistry

class JsonHttpBuilder extends HTTPBuilder {

    public JsonHttpBuilder(String url) {
        super(url)
        this.parser.'text/json' = { resp ->
            def bufferedText = resp.entity.content.getText(ParserRegistry.getCharset(resp)).trim()
            return new JsonSlurper().parseText(bufferedText)
        }
        this.parser.'application/json' = this.parser.'text/json'
    }
}
