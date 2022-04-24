package ar.edu.unahur.obj2.semillas

object inta {
    val parcelas = mutableSetOf<Parcela>()

    fun agregarParcelas(listaParcelas: List<Parcela>) {
        parcelas.addAll(listaParcelas)
    }

    fun promedioDePlantas(): Double {
        if (parcelas.isEmpty()) {
            return 0.0
        }
        return (parcelas.sumOf{ parcela -> parcela.cantidadPlantas()}).toDouble()  / parcelas.count()
    }



    fun parcelaMasAutosustentable(): Parcela? {
        return parcelas.filter {parcela -> parcela.cantidadPlantas() > 4 }.maxByOrNull { pla -> pla.promedioPlantasBienAsociadas()  }
    }

    fun quitarParcela(parcela: Parcela) {
        parcelas.remove(parcela)
    }


}