/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy;

import org.gradle.api.Action;
import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.component.ModuleComponentSelector;
import org.gradle.api.internal.artifacts.DependencySubstitutionInternal;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.dependencies.DefaultImmutableVersionConstraint;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.VersionSelectionReasons;
import org.gradle.internal.component.external.model.DefaultModuleComponentSelector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModuleForcingResolveRule implements Action<DependencySubstitutionInternal> {

    private final Map<ModuleIdentifier, String> forcedModules;

    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;

    public ModuleForcingResolveRule(Collection<? extends ModuleVersionSelector> forcedModules, ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.moduleIdentifierFactory = moduleIdentifierFactory;
        if (!forcedModules.isEmpty()) {
            this.forcedModules = new HashMap<ModuleIdentifier, String>();
            for (ModuleVersionSelector module : forcedModules) {
                this.forcedModules.put(moduleIdentifierFactory.module(module.getGroup(), module.getName()), module.getVersionConstraint().getPreferredVersion());
            }
        } else {
            this.forcedModules = null;
        }
    }

    @Override
    public void execute(DependencySubstitutionInternal details) {
        if (forcedModules == null) {
            return;
        }
        ModuleIdentifier key = moduleIdentifierFactory.module(details.getOldRequested().getGroup(), details.getOldRequested().getName());
        if (forcedModules.containsKey(key) && details.getRequested() instanceof ModuleComponentSelector) {
            details.useTarget(DefaultModuleComponentSelector.newSelector(key.getGroup(), key.getName(), new DefaultImmutableVersionConstraint(forcedModules.get(key))), VersionSelectionReasons.FORCED);
        }
    }
}
