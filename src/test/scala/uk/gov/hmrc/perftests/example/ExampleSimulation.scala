/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.example

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.ExampleRequests._

import java.time.LocalDate
import scala.util.Random

class ExampleSimulation extends PerformanceTestRunner {

  val random = new Random()

  val firstNames = Array("Firstname", "MiddleName", "Lastname", "TestNameFirst", "TestNameLast", "SomeFirstName", "SomeLastName")

  val lastNames = Array("Firstname", "MiddleName", "LastName", "TestNameFirst", "TestNameLast", "SomeFirstName", "SomeLastName")

  // Custom feeder that generates random data
  val randomPetData: Iterator[Map[String, String]] = Iterator.continually(Map(
    "petType" -> (if (random.nextBoolean()) "cat" else "dog"),
    "aroundChildren" -> random.nextBoolean().toString,

    // Valid dates for "from"
    "fromDay" -> f"${random.nextInt(28) + 1}%02d",
    "fromMonth" -> f"${random.nextInt(12) + 1}%02d",
    "fromYear" -> (LocalDate.now.getYear + random.nextInt(2)).toString,

    // Valid dates for "until"
    "untilDay" -> f"${random.nextInt(28) + 1}%02d",
    "untilMonth" -> f"${random.nextInt(12) + 1}%02d",
    "untilYear" -> (LocalDate.now.getYear + 1 + random.nextInt(2)).toString,

    // Account Details
    "accountName" -> s"${firstNames(random.nextInt(firstNames.length))} ${lastNames(random.nextInt(lastNames.length))}",
    "sortCode" -> f"${random.nextInt(1000000)}%06d",
    "accountNumber" -> f"${random.nextInt(100000000)}%08d",
    "buildingSocietyRoll" -> (if (random.nextBoolean())
      s"${('A' + random.nextInt(26)).toChar}${('A' + random.nextInt(26)).toChar}${('A' + random.nextInt(26)).toChar}${random.nextInt(1000)}"
    else "")
  ))

  def petDataFeeder: ChainBuilder = feed(randomPetData)

  // Apply the feeder to the first setup step
  setup("what-pet-looking-for", "What Pet Looking For")
    .withActions(petDataFeeder.actionBuilders: _*)
    .withRequests(startPage, whatPetLookingForForm, submitWhatPetLookingFor)

  setup("will-pet-be-around-children", "Will Pet Be Around Children") withRequests (willPetBeAroundChildren, submitWillPetBeAroundChildren)
  setup("when-want-pet-from", "When Want Pet From") withRequests (whenWantPetFrom, submitWhenWantPetFrom)
  setup("when-want-pet-until", "When Want Pet Until") withRequests (whenWantPetUntil, submitWhenWantPetUntil)
  setup("check-your-answers", "Check Your Answers") withRequests (checkYourAnswers, submitCheckYourAnswers)
  setup("pay-your-deposit", "Pay Your Deposit") withRequests (payYourDeposit, submitPayYourDeposit)
  setup("confirmation", "Confirmation") withRequests confirmation

  runSimulation()
}