package me.rayll.vendas.security.jwt


import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import me.rayll.vendas.VendasApplication
import me.rayll.vendas.entity.Usuario
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JWTService {

    @Value(value="\${security.jwt.expiracao}")
     var expiracao: String = ""

    @Value(value="\${security.jwt.chave-assinatura}")
     var chaveAssinatura: String = ""

    @Throws(ExpiredJwtException::class)
    private fun obterClaims(token: String) =
        Jwts.parser()
            .setSigningKey(chaveAssinatura)
            .parseClaimsJws(token)
            .body

    fun tokenValido(token: String): Boolean{
        try{
            var claims = obterClaims(token)
            var dataExpiracao = claims.expiration
            var date = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            return !LocalDateTime.now().isAfter(date)

        }catch (e: Exception){
            return false
        }
    }

    @Throws(ExpiredJwtException::class)
    fun obterLoginUsuario(token: String) = obterClaims(token).subject

    fun gerarToken(usuario: Usuario): String{

        val expString = expiracao.toLong()
        var dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString)
        var instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant()
        var date = Date.from(instant)

        return Jwts.builder()
            .setSubject(usuario.login)
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
            .compact()
    }

}
