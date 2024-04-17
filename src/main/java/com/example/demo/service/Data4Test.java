package com.example.demo.service;

public class Data4Test {
    public static class Admin {
        public static enum Date {
            radnomday(20),
            randommonth(12),
            randomyear(1950);
            public final int value;
            Date(int value) {
                this.value = value;
            }
        }

        public static enum Name {
            firstname("Amanda"),
            lastname("Kambus");
            public final String value;
            Name(String value) {
                this.value = value;
            }
            @Override
            public String toString() {
                return value;
            }
        }
    }
    public static class Normal {
        public static enum Date {
            radnomday(6),
            randommonth(6),
            randomyear(2000);
            public final int value;
            Date(int value) {
                this.value = value;
            }
        }

        public static enum Name {
            firstname("Julius"),
            lastname("Medikus");
            public final String value;
            Name(String value) {
                this.value = value;
            }
            @Override
            public String toString() {
                return value;
            }
        }
    }
}
