package dev.ikm.elk.snomed;

/*-
 * #%L
 * ELK Integration with SNOMED
 * %%
 * Copyright (C) 2023 Integrated Knowledge Management
 * %%
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
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import dev.ikm.elk.snomed.model.Concept;
import dev.ikm.elk.snomed.model.ConcreteRoleType;
import dev.ikm.elk.snomed.model.RoleType;

public class SnomedOntology {

	private HashMap<Long, Concept> conceptIdMap;

	private List<Concept> concepts;

	private HashMap<Long, RoleType> roleTypeIdMap;

	private List<RoleType> roleTypes;

	private HashMap<Long, ConcreteRoleType> concreteRoleTypeIdMap;

	private List<ConcreteRoleType> concreteRoleTypes;

	public Concept getConcept(long id) {
		return conceptIdMap.get(id);
	}

	public RoleType getRoleType(long id) {
		return roleTypeIdMap.get(id);
	}

	public ConcreteRoleType getConcreteRoleType(long id) {
		return concreteRoleTypeIdMap.get(id);
	}

	public List<Concept> getConcepts() {
		return concepts;
	}

	public List<RoleType> getRoleTypes() {
		return roleTypes;
	}

	public List<ConcreteRoleType> getConcreteRoleTypes() {
		return concreteRoleTypes;
	}

	public SnomedOntology(Collection<Concept> concepts, Collection<RoleType> roleTypes,
			Collection<ConcreteRoleType> concreteRoleTypes) {
		super();
		this.conceptIdMap = new HashMap<>();
		concepts.forEach(x -> conceptIdMap.put(x.getId(), x));
		this.concepts = new ArrayList<>(conceptIdMap.values());
		this.roleTypeIdMap = new HashMap<>();
		roleTypes.forEach(x -> roleTypeIdMap.put(x.getId(), x));
		this.roleTypes = new ArrayList<>(roleTypes);
		this.concreteRoleTypeIdMap = new HashMap<>();
		concreteRoleTypes.forEach(x -> concreteRoleTypeIdMap.put(x.getId(), x));
		this.concreteRoleTypes = new ArrayList<>(concreteRoleTypes);
	}

}
