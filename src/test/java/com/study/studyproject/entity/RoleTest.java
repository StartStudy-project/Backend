package com.study.studyproject.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


class RoleTest {

    @Test
    @DisplayName("역할 관련 타입인지 체크한다.")
    void containsRoleUser() {
        Role roleUser = Role.ROLE_USER;

        boolean result = Role.containsLoginRoleType(roleUser);

        assertThat(result).isTrue();
    }


    @Test
    @DisplayName("역할 관련 타입인지 체크한다.")
    void containsAdminUser() {
        Role roleUser = Role.ROLE_ADMIN;

        boolean result = Role.containsLoginRoleType(roleUser);

        assertThat(result).isTrue();
    }

    @DisplayName("역할 관련 타입인지 체크한다.")
    @CsvSource({"ROLE_USER, true, ROLE_ADMIN , true"})
    @ParameterizedTest
    void containsRoleType(Role role,boolean excepted) {

        boolean result = Role.containsLoginRoleType(role);

        assertThat(result).isEqualTo(excepted);

    }

}