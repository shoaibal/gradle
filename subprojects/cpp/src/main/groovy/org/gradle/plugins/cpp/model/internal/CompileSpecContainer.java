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
package org.gradle.plugins.cpp.model.internal;

import org.gradle.plugins.cpp.model.CompileSpec;
import org.gradle.plugins.cpp.model.NativeSourceSet;

import org.gradle.plugins.cpp.gcc.Gpp;
import org.gradle.plugins.cpp.gcc.GppCompileSpec;

import org.gradle.api.internal.ClassGenerator;
import org.gradle.api.internal.AbstractNamedDomainObjectContainer;
import org.gradle.api.internal.project.ProjectInternal;

class CompileSpecContainer extends AbstractNamedDomainObjectContainer<CompileSpec<?>> {

    final private ProjectInternal project;
    final private NativeSourceSet sourceSet;

    public CompileSpecContainer(ProjectInternal project, NativeSourceSet sourceSet, ClassGenerator classGenerator) {
        super((Class)CompileSpec.class, classGenerator);
        this.sourceSet = sourceSet;
        this.project = project;
    }

    protected CompileSpec<?> doCreate(String name) {
        return new GppCompileSpec(name, sourceSet, new Gpp(project.getFileResolver()), project);
    }
}