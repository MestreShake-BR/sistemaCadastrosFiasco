
package com.cadastrod.cadastro.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SimpleTokenService {

    // configure em application.properties: app.token.secret=CHAVE-BEM-GRANDE-32+CHARS
    @Value("${app.token.secret}")
    private String secret;

    private static final long TTL_MS = 24 * 60 * 60 * 1000;

    public String gerar(String email) {
        long exp = System.currentTimeMillis() + TTL_MS;
        String payload = email + "|" + exp;
        String mac = hmac(payload, secret);
        String raw = payload + "|" + mac;
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public String validarEObterEmail(String token) {
        try {
            String raw = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] p = raw.split("\\|");
            if (p.length != 3) throw new SecurityException("Formato de token inválido");
            String email = p[0];
            long exp = Long.parseLong(p[1]);
            String mac = p[2];

            String esperado = hmac(email + "|" + exp, secret);
            if (!constTimeEquals(mac, esperado)) throw new SecurityException("Assinatura inválida");
            if (System.currentTimeMillis() > exp) throw new SecurityException("Token expirado");

            return email;
        } catch (IllegalArgumentException e) {
            throw new SecurityException("Token inválido (base64)", e);
        }
    }

    private String hmac(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] out = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(out);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao calcular HMAC", e);
        }
    }

    private boolean constTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int res = 0;
        for (int i = 0; i < a.length(); i++) {
            res |= a.charAt(i) ^ b.charAt(i);
        }
        return res == 0;
    }
}
