package ar.edu.unahur.obj2.semillas

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

