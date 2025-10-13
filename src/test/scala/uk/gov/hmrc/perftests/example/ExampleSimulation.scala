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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.ExampleRequests._

class ExampleSimulation extends PerformanceTestRunner {

  setup("what-pet-looking-for", "What Pet Looking For") withRequests (startPage, whatPetLookingForForm, submitWhatPetLookingFor)

  setup("will-pet-be-around-children", "Will Pet Be Around Children") withRequests (willPetBeAroundChildren, submitWillPetBeAroundChildren)

  setup("when-want-pet-from", "When Want Pet From") withRequests (whenWantPetFrom, submitWhenWantPetFrom)

  setup("when-want-pet-until", "When Want Pet Until") withRequests (whenWantPetUntil, submitWhenWantPetUntil)

  setup("check-your-answers", "Check Your Answers") withRequests (checkYourAnswers, submitCheckYourAnswers)

  setup("pay-your-deposit", "Pay Your Deposit") withRequests (payYourDeposit, submitPayYourDeposit)

  setup("confirmation", "Confirmation") withRequests confirmation

  runSimulation()
}