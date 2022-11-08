package com.example.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    //有效时间
    private static long expire=604800;
    //密钥
    private static String secret="uiisndandsabufandqwjw";

    //根据用户id生成token
    public static String generateToken(Integer id){
        Date now=new Date();
        Date expiration=new Date(now.getTime()+1000*expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(String.valueOf(id))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    //解析token
    public static boolean checkByToken(String token){
            try{
                Claims claims=Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            }catch (Exception e){
    //解析出现问题，返回false
                e.printStackTrace();
                return false;
            }
    //解析无误，返回true
            return true;
        }
}