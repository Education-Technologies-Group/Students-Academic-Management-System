package repositories.user;

import models.user.UserModel;

/**
 * Repository interface for user data access operations.
 */
public interface UserRepositoryInterface {

    /**
     * Finds a user by their unique ID.
     *
     * @param id the user ID
     * @return the matching UserModel, or null if not found
     */
    UserModel getUserByID(int id);

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email
     * @return the matching UserModel, or null if not found
     */
    UserModel getUserByEmail(String email);
}
