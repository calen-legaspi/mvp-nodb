package com.orangeandbronze.enlistment;

import java.util.*;

import org.apache.commons.collections4.CollectionUtils;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

class Subject {

	private final String subjectId;
	private final Set<Subject> prerequisites;

	Subject(String subjectId, Collection<Subject> prerequisites) {
		notBlank(subjectId);
		isTrue(isAlphanumeric(subjectId), "sectionId must be alphanumeric, was: " + subjectId);
		notNull(prerequisites);
		this.subjectId = subjectId;
		Set<Subject> copy = new HashSet<>(prerequisites);
		copy.remove(null); // remove any null
		this.prerequisites = Collections.unmodifiableSet(copy);
	}

	/** Instantiate with no prerequisites. **/
	Subject(String subjectId) {
		this(subjectId, Collections.emptyList());
	}

	/** Checks if all prereqs present for this subject **/
	void checkPrereqs(Collection<Subject> subjectsTaken) {
		notNull(subjectsTaken);
		Set<Subject> copy = new HashSet<>(subjectsTaken);
		if (!copy.containsAll(prerequisites)) {
			throw new PrerequisiteException(
					"missing prereqs: " + CollectionUtils.removeAll(prerequisites, copy));
		}
	}
	
	String getSubjectId() {
		return subjectId;
	}

	@Override
	public String toString() {
		return subjectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prerequisites == null) ? 0 : prerequisites.hashCode());
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (prerequisites == null) {
			if (other.prerequisites != null)
				return false;
		} else if (!prerequisites.equals(other.prerequisites))
			return false;
		if (subjectId == null) {
			return other.subjectId == null;
		} else return subjectId.equals(other.subjectId);
	}

}
