// Original version from Riiid! (https://github.com/riiid) and Anvith Bhat (https://github.com/humblerookie), used under Apache licence 2.0

package uk.me.eddies.lib.gradle.github.release

import org.gradle.api.Plugin
import org.gradle.api.Project

class GithubPlugin implements Plugin<Project> {

    static final String EXTENSION_NAME = 'githubRelease'
    static final String TASK_NAME = 'githubRelease'

    void apply(Project project) {
        project.extensions.create(EXTENSION_NAME, GithubExtension)
        project.task(TASK_NAME, type: GithubReleaseTask) {
            group = 'dist'
            description = 'Publishes a release to Github.'
        }
    }
}
