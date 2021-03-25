package com.leothan.alguarisa.config

class Url {

    enum class NombreUrl{
        REGISTRO,
        LOGIN,
        RECUPERAR,
        AJUSTES,
        USUARIOS,
        CLAPS,
        FAMILIAS,
        MODULO_CLAP,
        FERIAS_CAMPO,
        PLAN_PROTEICO,
        TIENDA_FISICA,
        TIENDA_ENLINEA,
        TIENDA_MOVIL,
        BUSCAR_CLAP
    }

    fun direccion(name: NombreUrl): String{

        //val hosting = "http://192.168.250.9"
        val hosting = "http://alguarisa.sportec.info"
        val android = "/php-android"
        val laravel = "/proyecto/public"

        val local = false
        var adicional: String
        if (local){ adicional = "/appphp/proyecto" }else{ adicional = "" }

        return when(name){
            NombreUrl.REGISTRO -> "${hosting}${adicional}${android}/register.php"
            NombreUrl.LOGIN -> "${hosting}${adicional}${android}/login.php"
            NombreUrl.RECUPERAR -> "${hosting}${adicional}${android}/recuperar.php"
            NombreUrl.AJUSTES -> "${hosting}${adicional}${android}/update.php"
            NombreUrl.USUARIOS -> "${hosting}${laravel}/android/usuarios"
            //NombreUrl.CLAPS -> "${hosting}${laravel}/admin/claps"
            //NombreUrl.FAMILIAS -> "${hosting}${laravel}/admin/familias"
            NombreUrl.MODULO_CLAP -> "${hosting}${laravel}/android/modulo/clap"
            NombreUrl.FERIAS_CAMPO -> "${hosting}${laravel}/android/ferias/campo"
            NombreUrl.PLAN_PROTEICO -> "${hosting}${laravel}/android/plan/proteico"
            NombreUrl.TIENDA_FISICA -> "${hosting}${laravel}/android/tienda/fisica"
            NombreUrl.TIENDA_ENLINEA -> "${hosting}${laravel}/android/tienda/enlinea"
            NombreUrl.TIENDA_MOVIL -> "${hosting}${laravel}/android/tienda/movil"
            NombreUrl.BUSCAR_CLAP -> "${hosting}${laravel}/android/buscar/clap"
            else -> "/no/definida"
        }
    }
}