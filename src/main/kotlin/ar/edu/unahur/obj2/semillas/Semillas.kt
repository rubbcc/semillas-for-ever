package ar.edu.unahur.obj2.semillas

open class Planta(var altura: Double, val anioSemilla: Int) {

    open fun toleranciaAlSol(): Int = 7

    fun esFuerte(): Boolean = toleranciaAlSol() > 9

    fun daSemillas(): Boolean = esFuerte() || hayCondicionParaDarSemilla()

    open fun hayCondicionParaDarSemilla(): Boolean = false

}

class Menta(altura: Double, anioSemilla: Int): Planta(altura, anioSemilla) {

    fun espacio(): Double = altura + 1

    override fun hayCondicionParaDarSemilla(): Boolean = altura > 0.4
}

class Soja(altura: Double, anioSemilla: Int): Planta(altura, anioSemilla) {
    fun espacio(): Double = altura / 2

    override fun hayCondicionParaDarSemilla(): Boolean {
        return altura > 0.75 && altura < 0.9 && anioSemilla > 2007
    }

    override fun toleranciaAlSol(): Int {
        return if (altura < 0.5) 6 else if (altura > 1 ) 12 else 8
    }
}

class Quinoa(var espacio: Double, anioSemilla: Int): Planta(espacio, anioSemilla) {
    fun espacio(): Double = espacio

    override fun hayCondicionParaDarSemilla(): Boolean {
        return anioSemilla in (2001..2008)
    }

    override fun toleranciaAlSol(): Int {
        return if (espacio < 0.3) 10 else 7
    }
}
