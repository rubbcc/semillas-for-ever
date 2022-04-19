package ar.edu.unahur.obj2.semillas

open class Parcela {

    val ancho: Double
    val largo: Double
    val horasSol: Int
    val plantas: MutableSet<Planta>

    constructor(ancho: Double, largo: Double, horasSol: Int) {
        this.ancho = ancho
        this.largo = largo
        this.horasSol = horasSol
        this.plantas = mutableSetOf<Planta>()
    }

    fun plantar(listaPlantas: Collection<Planta>) {
        for (planta in listaPlantas) {
            if (planta.toleranciaAlSol() - horasSol < -1) {
                throw Exception("La parcela tiene muchas horas de sol")
            }
            else if (cantidadDePlantas() >= soportaNPlantas()) {
                throw Exception("La parcela esta llena y no se puede plantar más plantas")
            }
            else {
                plantas.add(planta)
            }
        }
    }

    fun superficie(): Double = ancho * largo

    fun soportaNPlantas(): Int = superficie().toInt() / 5

    fun cantidadDePlantas(): Int = plantas.count()

    fun tieneComplicaciones() = plantas.any{it.toleranciaAlSol() < horasSol}

    open fun seAsociaBien(planta: Planta): Boolean = false
}

class ParcelaEcologica(ancho: Double, largo: Double, horasSol: Int): Parcela(ancho, largo, horasSol) {
    override fun seAsociaBien(planta: Planta): Boolean = !tieneComplicaciones() && planta.esParcelaIdeal(this)
}

class ParcelaIndustrial(ancho: Double, largo: Double, horasSol: Int): Parcela(ancho, largo, horasSol) {
    override fun seAsociaBien(planta: Planta): Boolean = cantidadDePlantas() <= 2 && planta.esFuerte()
}