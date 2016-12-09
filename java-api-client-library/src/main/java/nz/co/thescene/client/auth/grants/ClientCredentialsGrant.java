/* 

Copyright (C) 2012-2016 TheScene.Co Ltd.
This file is part of TheScene.Co Java Client SDK.

TheScene.Co Java Client SDK is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TheScene.Co Java Client SDK is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License.

*/

package nz.co.thescene.client.auth.grants;

import nz.co.thescene.client.SceneClient;

public final class ClientCredentialsGrant extends Grant {

	@Override
	public String getUri() {

		if (SceneClient.getClientContext().getClientId() == null || SceneClient.getClientContext().getClientSecret() == null) { 
			throw new IllegalArgumentException("Client ID and Client Secret are not set");
		}

		return "/oauth/token?grant_type=client_credentials&client_id=" + SceneClient.getClientContext().getClientId() + "&client_secret=" + SceneClient.getClientContext().getClientSecret();
		
	}
	
}
