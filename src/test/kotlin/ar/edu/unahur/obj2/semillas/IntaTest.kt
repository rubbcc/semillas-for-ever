package ar.edu.unahur.obj2.semillas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class IntaTest: DescribeSpec ({
    describe("INTA") {
        val menta = Menta(1.0, 2001)
        val menta2 = Menta(0.5,2001)
        val soja = Soja(0.6,2009)
        val soja2 = Soja(1.3,2004)
        val quinoa = Quinoa(0.2, 2010)
        val quinoa2 = Quinoa(0.2, 2001)
        val sojaTransgenica = SojaTransgenica(1.1,2009)
        val peperina = Peperina(0.8, 2011)
        val parcela1 = Parcela(15.0, 2.0, 7)
        val parcela2 = Parcela(10.0, 1.5, 10)
        val parcela3 = Parcela(5.0, 5.0, 8)
        val parcela4 = Parcela(30.0, 1.0, 7)

        parcela1.convertirEnEcologica()
        parcela2.convertirEnEcologica()
        parcela3.convertirEnIndustrial()
        parcela4.convertirEnIndustrial()

        parcela1.plantar(listOf(menta,soja,quinoa,quinoa2,menta2))
        parcela2.plantar(listOf(quinoa,soja2))
        parcela3.plantar(listOf(sojaTransgenica,menta2,quinoa,quinoa2, peperina))
        parcela4.plantar(listOf(peperina,menta2,quinoa))

        it("como no hay parcelas el promedio de plantas por parcela es 0") {
            inta.promedioDePlantas().shouldBe(0.0)
        }

        it("Agregando parcelas al INTA"){
            inta.agregarParcelas(listOf(parcela1,parcela2,parcela3))
            inta.parcelas.shouldContainAll(parcela1,parcela2,parcela3)
        }

        it("hay un promedio de 4 plantas por parcela") {
            inta.promedioDePlantas().shouldBe(4)
        }

        it("si agrego la parcela4 hay un promedio de 3,75 plantas por parcela") {
            inta.agregarParcelas(listOf(parcela4))
            inta.promedioDePlantas().shouldBe(3.75)
        }

        it("para que una parcela sea autosustentable tiene que tener mas de 4 plantas(parcela1 y parcela3)") {
            inta.parcelas.filter { parcela -> parcela.cantidadPlantas() > 4 }.shouldContainAll(parcela1,parcela3)
        }

        it("para que sea la más autosustentable tiene que tener el mayor porcentaje de plantas bien asociadas(parcela1)") {
            inta.parcelaMasAutosustentable().shouldNotBe(parcela2)
            inta.parcelaMasAutosustentable().shouldNotBe(parcela3)
            inta.parcelaMasAutosustentable().shouldNotBe(parcela4)
            inta.parcelaMasAutosustentable().shouldBe(parcela1)
        }

        it("quitando la parcela1") {
            inta.quitarParcela(parcela1)
            inta.parcelas.shouldContainAll(parcela2,parcela3,parcela4)
        }

        it("ahora la más sustentable es la parcela3") {
            inta.parcelaMasAutosustentable().shouldNotBe(parcela1)
            inta.parcelaMasAutosustentable().shouldNotBe(parcela2)
            inta.parcelaMasAutosustentable().shouldNotBe(parcela4)
            inta.parcelaMasAutosustentable().shouldBe(parcela3)
        }

        it("si quito tambien la parcela3, ninguna es la mas sustentable") {
            inta.quitarParcela(parcela3)
            inta.parcelaMasAutosustentable().shouldBe(null)
        }
    }
})