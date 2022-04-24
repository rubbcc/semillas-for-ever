package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith

class Parcelas : DescribeSpec ({
    describe("Creación de Parcelas") {
        // val parcela = Parcela(ancho, largo, horasSol)
        val parcela = Parcela(4.0, 7.0, 9)

        val sojaChica = Soja(0.6, 2009)
        val soja = Soja(0.7, 2002)
        val sojaMedia = Soja(0.9, 2004)
        val sojaAlta = Soja(1.2, 20012)
        val sojaMuyAlta = Soja(1.6, 2010)

        val quinoaChica = Quinoa(0.2, 2018)
        val quinoa = Quinoa(0.5, 2018)

        it("Calculo superficie parcela") {
            parcela.superficie().shouldBe(28)
        }
        it("Parcela soporta x cantidad de plantas") {
            parcela.soportaNPlantas().shouldBe(5)
        }

        it("Se agrega la soja y la parcela tiene complicaciones") {
            parcela.tieneComplicaciones().shouldBeFalse()
            soja.plantarEn(parcela)
            parcela.tieneComplicaciones().shouldBeTrue()
        }

        it("se agrega una planta supere el umbral de horas de sol toleradas") {
            val muchoSol = shouldThrow<Exception> {
                quinoa.plantarEn(parcela)
            }
            muchoSol.message should startWith("La parcela tiene muchas horas de sol")
        }
        it("Se agregan plantas y la parcela se queda sin espacio") {
            parcela.plantar(listOf(sojaChica, sojaMedia, sojaAlta, sojaMuyAlta))
            val parcelaLlena = shouldThrow<Exception> {
                quinoaChica.plantarEn(parcela)
            }
            parcelaLlena.message should startWith("La parcela esta llena y no se puede plantar más plantas")
        }
    }

    describe("Parcelas ideales") {
        val parcela = Parcela(4.0, 7.0, 10)
        val parcelaChica = Parcela(2.0, 2.5, 12)
        val parcelaMonocultivo = Parcela(5.0, 1.0, 13)

        val menta = Menta(1.0, 2021)

        val soja = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)

        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)

        soja.plantarEn(parcelaChica)

        it("La menta y peperina prefieren una superficie mayor a 6 metros cuadrados") {
            menta.esParcelaIdeal(parcela).shouldBeTrue()
            menta.esParcelaIdeal(parcelaChica).shouldBeFalse()

            peperina.esParcelaIdeal(parcela).shouldBeTrue()
            peperina.esParcelaIdeal(parcelaChica).shouldBeFalse()
        }
        it("La quinoa prefiere que no haya ninguna planta cuya altura supere los 1.5 metros") {
            quinoa.esParcelaIdeal(parcela).shouldBeTrue()
            quinoa.esParcelaIdeal(parcelaChica).shouldBeFalse()
            quinoa.esParcelaIdeal(parcelaMonocultivo).shouldBeTrue()
        }
        it("La soja prefiere que las horas de sol sean exactamente igual a los que ella tolera") {
            soja.esParcelaIdeal(parcela).shouldBeFalse()
            soja.esParcelaIdeal(parcelaChica).shouldBeTrue()
            soja.esParcelaIdeal(parcelaMonocultivo).shouldBeFalse()
        }
        it("La soja transgenica prefiere parcelas que tengan 1 como maxima cantidad de plantas") {
            sojaTransgenica.esParcelaIdeal(parcela).shouldBeFalse()
            sojaTransgenica.esParcelaIdeal(parcelaChica).shouldBeTrue()
            sojaTransgenica.esParcelaIdeal(parcelaMonocultivo).shouldBeTrue()
        }
    }

    describe("Asociacion de parcelas") {
        val parcelaEcologica = Parcela(4.0, 8.0, 6)
        val parcelaEcoVerano = Parcela(4.0, 8.0, 13)
        val parcelaIndustrial = Parcela(4.0, 8.0, 12)

        parcelaEcologica.convertirEnEcologica()
        parcelaEcoVerano.convertirEnEcologica()
        parcelaIndustrial.convertirEnIndustrial()

        val soja = Soja(1.2, 2009)
        val sojaAlta = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)
        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)

        it("Nos aseguramos que cada parcela sea lo que es") {
            parcelaEcologica.tipoParcela().shouldBe("Ecologica")
            parcelaEcoVerano.tipoParcela().shouldBe("Ecologica")
            parcelaIndustrial.tipoParcela().shouldBe("Industrial")
        }

        it("Parcela Ecologica") {
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeTrue()
            soja.seAsociaBien(parcelaEcoVerano).shouldBeFalse()
            soja.seAsociaBien(parcelaEcologica).shouldBeFalse()
            soja.plantarEn(parcelaEcoVerano)
            peperina.seAsociaBien(parcelaEcologica).shouldBeTrue()
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeFalse()
        }

        it("Parcela Industrial") {
            quinoa.seAsociaBien(parcelaIndustrial).shouldBeFalse()
            soja.seAsociaBien(parcelaIndustrial).shouldBeTrue()
            parcelaIndustrial.plantar(listOf(sojaAlta, sojaTransgenica, soja))
            soja.seAsociaBien(parcelaIndustrial).shouldBeFalse()
        }
    }
})