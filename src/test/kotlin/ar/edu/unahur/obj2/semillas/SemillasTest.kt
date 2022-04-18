package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith

class SemillasTest : DescribeSpec ({
    // hay una clase Planta que tiene por atributos
    // anioSemilla, altura
    describe("Creación de las plantas") {
        val menta = Menta(1.0, 2021)
        val mentita = Menta(0.3, 2021)

        val soja = Soja(0.6, 2009)
        val sojaEspecial = Soja(0.8, 2008)
        val sojaAlta = Soja(1.2, 2009)

        val quinoa = Quinoa(0.5, 2018)
        val quinoaEspecial = Quinoa(0.9, 2006)
        val quinoaChica = Quinoa(0.2, 2000)

        it("probamos los atributos altura  y anioSemilla") {
            menta.altura.shouldBe(1.0)
            menta.anioSemilla.shouldBe(2021)
        }

        it("verificar si da semillas") {
            menta.daSemillas().shouldBeTrue()
            mentita.daSemillas().shouldBeFalse()

            soja.daSemillas().shouldBeFalse()
            sojaEspecial.daSemillas().shouldBeTrue()
            sojaAlta.daSemillas().shouldBeTrue()

            quinoa.daSemillas().shouldBeFalse()
            quinoaEspecial.daSemillas().shouldBeTrue()
            quinoaChica.daSemillas().shouldBeTrue()
        }

        it("es fuerte") {
            menta.esFuerte().shouldBeFalse()
            mentita.esFuerte().shouldBeFalse()

            soja.esFuerte().shouldBeFalse()
            sojaEspecial.esFuerte().shouldBeFalse()
            sojaAlta.esFuerte().shouldBeTrue()

            quinoa.esFuerte().shouldBeFalse()
            quinoaEspecial.esFuerte().shouldBeFalse()
            quinoaChica.esFuerte().shouldBeTrue()
        }

        it("espacio") {
            menta.espacio().shouldBe(2.0)
            mentita.espacio().shouldBe(1.3)

            soja.espacio().shouldBe(0.3)
            sojaEspecial.espacio().shouldBe(0.4)
            sojaAlta.espacio().shouldBe(0.6)

            quinoa.espacio().shouldBe(0.5)
            quinoaEspecial.espacio().shouldBe(0.9)
            quinoaChica.espacio().shouldBe(0.2)
        }

        it("verifico la suma de varias") {
            val superficie = mutableListOf(
                soja.espacio(),
                menta.espacio(),
                mentita.espacio()
            ).sum()
            Math.ceil(superficie).shouldBe(4)
        }
    }
})

class Variedades : DescribeSpec ({
    describe("Creación de sojas transgenicas") {
        val sojaTransgenicaVieja = SojaTransgenica(0.2,2005)
        val sojaTransgenicaBaja = SojaTransgenica(0.2,2008)
        val sojaTransgenicaAlta = SojaTransgenica(0.8,2008)
        val sojaTransgenicaMuyAlta = SojaTransgenica(1.0,2002)

        it("La soja transgenica nunca da semillas") {
            sojaTransgenicaVieja.daSemillas().shouldBeFalse()
            sojaTransgenicaBaja.daSemillas().shouldBeFalse()
            sojaTransgenicaAlta.daSemillas().shouldBeFalse()
            sojaTransgenicaMuyAlta.daSemillas().shouldBeFalse()
        }
    }
    describe("Creación de peperinas") {
        val menta = Menta(0.3, 2021)
        val peperina = Peperina(0.3, 2021)

        it("La peperina ocupa el doble del espacio que la menta") {
            menta.espacio().shouldBe(1.3)
            peperina.espacio().shouldBe(2.6)
        }
    }
})


class Parcelas : DescribeSpec ({
    describe("Creación de Parcelas") {
        // val parcela = Parcela(ancho, largo, horasSol)
        val parcela = Parcela(4.0, 7.0, 10)

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
        it("Agrega plantas") {
            parcela.plantar(listOf(sojaChica,soja,sojaMedia,sojaAlta))

            parcela.plantas().shouldBe(setOf(sojaChica,soja,sojaMedia,sojaAlta))
        }

        it("Agrega otra planta sin espacio o que supere el umbral de horas de sol toleradas") {
            val muchoSol = shouldThrow<Exception> {
                quinoa.plantarEn(parcela)
            }
            muchoSol.message should startWith("La parcela tiene muchas horas de sol")

            sojaMuyAlta.plantarEn(parcela)
            val parcelaLlena = shouldThrow<Exception> {
                quinoaChica.plantarEn(parcela)
            }
            parcelaLlena.message should startWith("La parcela esta llena y no se puede plantar más plantas")
        }
    }

    describe("Parcelas ideales") {
        val parcela = Parcela(4.0, 7.0, 10)
        val parcelaChica = Parcela(2.0, 2.0, 12)
        val parcelaMonocultivo = Parcela(1.0, 1.0, 13)

        val menta = Menta(1.0, 2021)

        val soja = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)

        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)

        soja.plantarEn(parcelaChica)

        it("La menta y peperina prefieren una superficie mayor a 6 metros cuadrados") {
            menta.parcelaIdeal(parcela).shouldBeTrue()
            menta.parcelaIdeal(parcelaChica).shouldBeFalse()

            peperina.parcelaIdeal(parcela).shouldBeTrue()
            peperina.parcelaIdeal(parcelaChica).shouldBeFalse()
        }
        it("La quinoa prefiere que no haya ninguna planta cuya altura supere los 1.5 metros") {
            quinoa.parcelaIdeal(parcela).shouldBeTrue()
            quinoa.parcelaIdeal(parcelaChica).shouldBeFalse()
            quinoa.parcelaIdeal(parcelaMonocultivo).shouldBeTrue()
        }
        it("La soja prefiere que las horas de sol sean exactamente igual a los que ella tolera") {
            soja.parcelaIdeal(parcela).shouldBeFalse()
            soja.parcelaIdeal(parcelaChica).shouldBeTrue()
            soja.parcelaIdeal(parcelaMonocultivo).shouldBeFalse()
        }
        it("La soja transgenica prefiere parcelas que tengan 1 como maxima cantidad de plantas") {
            sojaTransgenica.parcelaIdeal(parcela).shouldBeFalse()
            sojaTransgenica.parcelaIdeal(parcelaChica).shouldBeFalse()
            sojaTransgenica.parcelaIdeal(parcelaMonocultivo).shouldBeTrue()
        }
    }

    describe("Asociacion de parcelas") {
        val parcelaEcologica = ParcelaEcologica(4.0,8.0,6)
        val parcelaEcoVerano = ParcelaEcologica(4.0,8.0,12)
        val parcelaIndustrial = ParcelaEcologica(4.0,8.0,12)
        val parcelaIndustVerano = ParcelaEcologica(4.0,8.0,13)

        val soja = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)
        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)


        it("Parcela Ecologica") {
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeFalse()
            soja.plantarEn(parcelaEcoVerano)
            peperina.seAsociaBien(parcelaEcologica).shouldBeTrue()
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeFalse()
        }

        it("Parcela Industrial") {
            quinoa.seAsociaBien(parcelaIndustrial).shouldBeFalse()
            soja.seAsociaBien(parcelaIndustrial).shouldBeTrue()
            parcelaIndustrial.plantar(listOf(peperina,sojaTransgenica,soja))
            soja.seAsociaBien(parcelaIndustrial).shouldBeFalse()
            soja.seAsociaBien(parcelaIndustVerano).shouldBeFalse()
        }
    }

    describe("INTA") {
        val menta = Menta(1.0, 2001)
        val menta2 = Menta(0.5,2001)
        val soja = Soja(0.6,2009)
        val soja2 = Soja(0.3,2004)
        val quinoa = Quinoa(0.5, 2010)
        val quinoa2 = Quinoa(0.5, 2001)
        val sojaTransgenica = SojaTransgenica(1.1,2009)
        val peperina = Peperina(0.8, 2011)
        val parcela1 = ParcelaEcologica(15.0, 2.0, 7)
        val parcela2 = ParcelaEcologica(10.0, 1.5, 10)
        val parcela3 = ParcelaIndustrial(5.0, 5.0, 9)
        val parcela4 = ParcelaIndustrial(30.0, 1.0, 11)

        parcela1.plantar(listOf(menta,soja,quinoa,quinoa2,menta2))
        parcela2.plantar(listOf(quinoa,soja2))
        parcela3.plantar(listOf(sojaTransgenica,soja,quinoa,quinoa2, peperina))
        parcela4.plantar(listOf(peperina,menta2,quinoa))

        it("Agregando parcelas al INTA"){
            inta.agregarParcelas(listOf(parcela1,parcela2,parcela3,parcela4))
            inta.parcelas().shouldBe(setOf(parcela1,parcela2,parcela3,parcela4))
        }

        it("hay un promedio de 3,75 plantas por parcela") {
            inta.promedioDePlantas().shouldBe(3.75)
        }

        it("la parcela mas autosustentable es la parcela1") {
            inta.parcelaMasAutosustentable().shouldBe(parcela1)
        }
    }
})

