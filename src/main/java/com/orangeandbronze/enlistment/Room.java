package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;

import java.util.*;

class Room {

	private final String name;
	private final int capacity;
	private final Collection<Section> sections = new HashSet<>();

	Room(String name, int capacity) {
		this(name, capacity, Collections.emptyList());
	}

	Room(String name, int capacity, Collection<Section> sections) {
		notBlank(name, "name cannot be blank or null");
		isTrue(isAlphanumeric(name), "sectionId must be alphanumeric, was: " + name);
		isTrue(capacity > 0, "capacity must be greater than 0, was: " + capacity);
		this.name = name;
		this.capacity = capacity;
		this.sections.addAll(sections);
		this.sections.remove(null);
	}

	void add(Section newSection) {
		sections.forEach(currSection -> {
			if (currSection.hasScheduleConflict(newSection)) {
				throw new ScheduleConflictException("New section " + newSection
						+ " has a schedule conflict with current section " + currSection + " for room " + this);
			}
		});
		sections.add(newSection);
	}

	void checkCapacity(int occupancy) {
		if (occupancy >= capacity) {
			throw new SectionCapacityException(
					"occupancy of " + occupancy + " is at or exceeds capacity of " + capacity);
		}
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Room room = (Room) o;

		return name != null ? name.equals(room.name) : room.name == null;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
