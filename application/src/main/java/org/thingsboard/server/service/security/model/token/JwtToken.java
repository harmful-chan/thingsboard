package org.thingsboard.server.service.security.model.token;

import java.io.Serializable;

public interface JwtToken extends Serializable {
    String getToken();
}
