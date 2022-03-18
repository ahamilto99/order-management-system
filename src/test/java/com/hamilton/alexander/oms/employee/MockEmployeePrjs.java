package com.hamilton.alexander.oms.employee;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class MockEmployeePrjs {

    private static Random rand = new Random();

    public static final Long WAYNE_ID = rand.nextLong(1L, Long.MAX_VALUE);

    public static final Long AL_ID = rand.nextLong(1L, Long.MAX_VALUE);

    public static final Long DON_ID = rand.nextLong(1L, Long.MAX_VALUE);

    public static final UUID WAYNE_SUB = UUID.randomUUID();

    public static final UUID AL_SUB = UUID.randomUUID();

    public static final UUID DON_SUB = UUID.randomUUID();

    public static final EmployeePrj WAYNE = new EmployeePrj() {

        @Override
        public String getPhone() {
            return "613-555-9999";
        }

        @Override
        public String getLastName() {
            return "Coady";
        }

        @Override
        public Long getId() {
            return WAYNE_ID;
        }

        @Override
        public String getFirstName() {
            return "Wayne";
        }

        @Override
        public String getEmail() {
            return "wcoady@oms.com";
        }

        @Override
        public UUID getJwtSub() {
            return WAYNE_SUB;
        }

    };

    public static final EmployeePrj AL = new EmployeePrj() {

        @Override
        public String getPhone() {
            return "613-555-2424";
        }

        @Override
        public String getLastName() {
            return "Hamilton";
        }

        @Override
        public Long getId() {
            return AL_ID;
        }

        @Override
        public String getFirstName() {
            return "Al";
        }

        @Override
        public String getEmail() {
            return "aham@oms.com";
        }

        @Override
        public UUID getJwtSub() {
            return AL_SUB;
        }

    };

    public static final EmployeePrj DON = new EmployeePrj() {

        @Override
        public String getPhone() {
            return "613-555-1234";
        }

        @Override
        public String getLastName() {
            return "Hamilton";
        }

        @Override
        public Long getId() {
            return DON_ID;
        }

        @Override
        public String getFirstName() {
            return "Don";
        }

        @Override
        public String getEmail() {
            return "dham@oms.com";
        }

        @Override
        public UUID getJwtSub() {
            return DON_SUB;
        }

    };

    public static final Page<EmployeePrj> ALL = new PageImpl<EmployeePrj>(List.of(WAYNE, AL, DON));

}
