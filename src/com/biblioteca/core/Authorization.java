package com.biblioteca.core;

import java.util.List;

public interface Authorization {
    enum Level { ADMINISTRATOR, CREATE, READ, UPDATE, DELETE, GRANT_LOAN }

    List<Level> getAuthorizations();
}
