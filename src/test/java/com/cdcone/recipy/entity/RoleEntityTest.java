package com.cdcone.recipy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RoleEntityTest {
    /**
     * Method under test: {@link RoleEntity#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new RoleEntity()).canEqual("Other"));
    }

    /**
     * Method under test: {@link RoleEntity#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        RoleEntity roleEntity = new RoleEntity();

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertTrue(roleEntity.canEqual(roleEntity1));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link RoleEntity}
     *   <li>{@link RoleEntity#setId(Long)}
     *   <li>{@link RoleEntity#setName(String)}
     * </ul>
     */
    @Test
    void testConstructor() {
        RoleEntity actualRoleEntity = new RoleEntity();
        actualRoleEntity.setId(123L);
        actualRoleEntity.setName("Name");
        assertEquals(123L, actualRoleEntity.getId().longValue());
        assertEquals("Name", actualRoleEntity.getName());
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        assertNotEquals(roleEntity, null);
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals2() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        assertNotEquals(roleEntity, "Different type to RoleEntity");
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals3() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");
        assertEquals(roleEntity, roleEntity);
        int expectedHashCodeResult = roleEntity.hashCode();
        assertEquals(expectedHashCodeResult, roleEntity.hashCode());
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals4() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("Name");

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertEquals(roleEntity, roleEntity1);
        int expectedHashCodeResult = roleEntity.hashCode();
        assertEquals(expectedHashCodeResult, roleEntity1.hashCode());
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals5() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("Name");

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertNotEquals(roleEntity, roleEntity1);
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals6() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(null);
        roleEntity.setName("Name");

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertNotEquals(roleEntity, roleEntity1);
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals7() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName(null);

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertNotEquals(roleEntity, roleEntity1);
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals8() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName("com.cdcone.recipy.entity.RoleEntity");

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName("Name");
        assertNotEquals(roleEntity, roleEntity1);
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals9() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(null);
        roleEntity.setName("Name");

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(null);
        roleEntity1.setName("Name");
        assertEquals(roleEntity, roleEntity1);
        int expectedHashCodeResult = roleEntity.hashCode();
        assertEquals(expectedHashCodeResult, roleEntity1.hashCode());
    }

    /**
     * Method under test: {@link RoleEntity#equals(Object)}
     */
    @Test
    void testEquals10() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setName(null);

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(123L);
        roleEntity1.setName(null);
        assertEquals(roleEntity, roleEntity1);
        int expectedHashCodeResult = roleEntity.hashCode();
        assertEquals(expectedHashCodeResult, roleEntity1.hashCode());
    }
}

