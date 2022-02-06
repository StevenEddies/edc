// Original version from Riiid! (https://github.com/riiid) and Anvith Bhat (https://github.com/humblerookie), used under Apache licence 2.0

package uk.me.eddies.lib.gradle.github.release

import org.gradle.api.Plugin
import org.gradle.api.Project

class GithubPlugin implements Plugin<Project> {

    private static final String NAME = 'github'

    void apply(Project project) {
        project.extensions.create(NAME, GithubExtension)
        project.task('githubRelease', type: GithubReleaseTask)
    }
}
