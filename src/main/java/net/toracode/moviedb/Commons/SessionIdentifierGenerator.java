package net.toracode.moviedb.Commons;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by sayemkcn on 10/25/16.
 */
public class SessionIdentifierGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}