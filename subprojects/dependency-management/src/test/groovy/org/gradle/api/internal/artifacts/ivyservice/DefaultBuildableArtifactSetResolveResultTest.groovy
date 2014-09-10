/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.ivyservice
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ArtifactResolveException
import org.gradle.internal.component.model.ComponentArtifactMetaData
import spock.lang.Specification

class DefaultBuildableArtifactSetResolveResultTest extends Specification {
    final result = new DefaultBuildableArtifactSetResolveResult()

    def "cannot get artifacts when no result specified"() {
        when:
        result.artifacts

        then:
        IllegalStateException e = thrown()
        e.message == 'No result has been specified.'
    }

    def "cannot get failure when no result specified"() {
        when:
        result.failure

        then:
        IllegalStateException e = thrown()
        e.message == 'No result has been specified.'
    }

    def "cannot get artifacts when resolve failed"() {
        def failure = new ArtifactResolveException("broken")

        when:
        result.failed(failure)
        result.artifacts

        then:
        ArtifactResolveException e = thrown()
        e == failure
    }

    def "has result when artifacts set"() {
        when:
        def artifact = Mock(ComponentArtifactMetaData)
        result.resolved([artifact])

        then:
        result.hasResult()
        result.failure == null
        result.artifacts == [artifact] as Set
    }

    def "has result when failure set"() {
        when:
        final failure = new ArtifactResolveException("bad")
        result.failed(failure)

        then:
        result.hasResult()
        result.failure == failure
    }
}
