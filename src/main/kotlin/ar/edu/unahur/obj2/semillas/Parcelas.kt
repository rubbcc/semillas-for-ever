package ar.edu.unahur.obj2.semillas

open class Parcela(val ancho: Double, val largo: Double, val horasSol: Int) {

    val plantas: MutableSet<Planta> = mutableSetOf<Planta>()
    var esParcelaEcologica = false

    fun plantar(listaPlantas: Collection<Planta>) {
        for (planta in listaPlantas) {
            if (planta.toleranciaAlSol() - horasSol < -1) {
                throw Exception("La parcela tiene muchas horas de sol")
            }
            else if (cantidadPlantas() >= soportaNPlantas()) {
                throw Exception("La parcela esta llena y no se puede plantar más plantas")
            }
            else {
                plantas.add(planta)
            }
        }
    }

    fun superficie(): Double = ancho * largo

    fun soportaNPlantas(): Int = superficie().toInt() / 5

    fun cantidadPlantas(): Int = plantas.count()

    fun cantidadBienAsociadas(): Int = plantas.count { planta -> planta.seAsociaBien(this) }

    fun tieneComplicaciones() = plantas.any{it.toleranciaAlSol() < horasSol}

    fun convertirEnEcologica() {
        esParcelaEcologica = true
    }

    fun convertirEnIndustrial() {
        esParcelaEcologica = false
    }

    fun seAsociaBien(planta: Planta): Boolean {
        if (esParcelaEcologica) {
            return !tieneComplicaciones() && planta.esParcelaIdeal(this);
        }
        else {
            return cantidadPlantas() <= 2 && planta.esFuerte()
        }
    }

    fun tipoParcela(): String {
        if (esParcelaEcologica) {
            return "Ecologica";
        }
        else {
            return "Industrial"
        }
    }

    fun promedioPlantasBienAsociadas(): Double {
        return cantidadBienAsociadas().toDouble() / cantidadPlantas()
    }
}