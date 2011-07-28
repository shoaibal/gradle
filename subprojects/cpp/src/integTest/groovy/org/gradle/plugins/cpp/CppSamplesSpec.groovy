/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.plugins.cpp

import org.gradle.integtests.fixtures.*
import org.gradle.integtests.fixtures.internal.*
import org.junit.*

class CppSamplesSpec extends AbstractIntegrationSpec {
    
    @Rule public final Sample exewithlib = new Sample('cpp/exewithlib')
    
    def "exe with lib"() {
        given:
        sample exewithlib
        
        when:
        run "build"
        
        then:
        ":exe:compileMainMain" in executedTasks
        
        and:
        file("cpp", "exewithlib", "exe", "build", "binaries", "mainMain").exec().out == "Hello, World!\n"
    }
    
    
}