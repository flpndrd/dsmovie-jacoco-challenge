package com.devsuperior.dsmoviejacoco.user;

import com.devsuperior.dsmoviejacoco.user.entity.Role;
import com.devsuperior.dsmoviejacoco.user.entity.User;

public final class UserFixture {

	public static final String CLIENT_USERNAME = "maria@gmail.com";

	public static final String CLIENT_AUTHORITY = "ROLE_CLIENT";

	private UserFixture() {
	}

	public static User aClientUser() {
		User user = new User("Maria", CLIENT_USERNAME, "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.getRoles().add(new Role(CLIENT_AUTHORITY));
		return user;
	}

}
