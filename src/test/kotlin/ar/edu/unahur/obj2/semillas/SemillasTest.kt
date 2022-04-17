package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

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
        val parcela = Parcela(4, 7, 12)
        val menta = Menta(1.0, 2021)
        val mentita = Menta(0.3, 2021)

        val soja = Soja(0.6, 2009)
        val sojaAlta = Soja(1.2, 2009)

        val quinoa = Quinoa(0.5, 2018)
        val quinoaChica = Quinoa(0.2, 2000)

        it("Calculo superficie parcela") {
            parcela.superficie().shouldBe(28)
        }
        it("Parcela soporta x cantidad de plantas") {
            parcela.soportaNPlantas().shouldBe(5)
        }
        it("Agrega plantas") {
            parcela.agregaPlanta(menta)
            parcela.agregaPlanta(mentita)
            parcela.agregaPlanta(soja)
            parcela.agregaPlanta(sojaAlta)
            parcela.agregaPlanta(quinoa)

            parcela.plantas().shouldBe(setOf(menta, mentita, soja, sojaAlta, quinoa))
        }

        it("Agrega otra planta sin espacio") {
            parcela.agregaPlanta(quinoaChica)
            // shouldBe ERROR
        }
    }

    describe("Parcelas ideales") {
        val parcela = Parcela(4, 7, 10)
        val parcelaChica = Parcela(2, 2, 12)
        val parcelaMonocultivo = Parcela(1, 1, 16)

        val menta = Menta(1.0, 2021)

        val soja = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)

        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)

        parcelaChica.agregarPlanta(soja)

        it("Planta prefiere esta parcela") {
            menta.parcelaIdeal(parcela).shouldBeTrue()
            menta.parcelaIdeal(parcelaChica).shouldBeFalse()

            peperina.parcelaIdeal(parcela).shouldBeTrue()
            peperina.parcelaIdeal(parcelaChica).shouldBeFalse()

            quinoa.parcelaIdeal(parcela).shouldBeTrue()
            quinoa.parcelaIdeal(parcelaChica).shouldBeFalse()
            quinoa.parcelaIdeal(parcelaMonocultivo).shouldBeTrue()

            soja.parcelaIdeal(parcela).shouldBeFalse()
            soja.parcelaIdeal(parcelaChica).shouldBeTrue()
            soja.parcelaIdeal(parcelaMonocultivo).shouldBeFalse()

            sojaTransgenica.parcelaIdeal(parcela).shouldBeFalse()
            sojaTransgenica.parcelaIdeal(parcelaChica).shouldBeFalse()
            sojaTransgenica.parcelaIdeal(parcelaMonocultivo).shouldBeTrue()
        }
    }

    La asociación de plantas es una práctica ancestral que busca maximizar los beneficios de las plantas al plantarlas en conjunto con otras que de alguna manera potencian sus beneficios. Para modelar esto, debemos previamente diferenciar las parcelas en dos tipos: las ecológicas y las industriales.

    Para saber si una planta se asocia bien dentro de una parcela, hay que tener en cuenta:

    para las parcelas ecológicas: que la parcela no tenga complicaciones y sea ideal para la planta;
    para las parcelas industriales: que haya como máximo 2 plantas y que la planta en cuestión sea fuerte.

    describe("Asociacion de parcelas") {
        val parcelaEcologica = ParcelaEcologica(4,8,6)
        val parcelaEcoVerano = ParcelaEcologica(4,8,12)
        val parcelaIndustrial = ParcelaEcologica(4,8,14)
        val parcelaIndustVerano = ParcelaEcologica(4,8,16)

        val soja = Soja(1.6, 2009)
        val sojaTransgenica = SojaTransgenica(1.2, 2009)
        val quinoa = Quinoa(0.5, 2018)
        val peperina = Peperina(0.2, 2000)


        it("Parcela Ecologica") {
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeFalse()
            parcelaEcoVerano.agregarPlanta(soja)
            peperina.seAsociaBien(parcelaEcologica).shouldBeTrue()
            peperina.seAsociaBien(parcelaEcoVerano).shouldBeFalse()

        }

        it("Parcela Industrial") {
            quinoa.seAsociaBien(parcelaIndustrial).shouldBeFalse()
            soja.seAsociaBien(parcelaIndustrial).shouldBeTrue()
            parcelaIndustrial.agregarPlanta(peperina)
            parcelaIndustrial.agregarPlanta(sojaTransgenica)
            parcelaIndustrial.agregarPlanta(soja)
            soja.seAsociaBien(parcelaIndustrial).shouldBeFalse()
            soja.seAsociaBien(parcelaIndustVerano).shouldBeFalse()
        }
    }

})

