package com.agricola.backend.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.agricola.backend.models.entity.Usuario;
import com.agricola.backend.models.services.IUsuarioService;

@Component
public class infoAdicionalToken implements TokenEnhancer{

	 @Autowired
	 private  IUsuarioService usuarioService;
	 
	 
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Map <String,Object> info = new HashMap<>();
		
		info.put("info adicional", "Hola que tal : ".concat(authentication.getName()));
		info.put("ID", usuario.getId());
		info.put("nombre", usuario.getNombre());
		info.put("email", usuario.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		return accessToken;
	}

}
