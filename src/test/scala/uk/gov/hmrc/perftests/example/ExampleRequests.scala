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
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object ExampleRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("example-frontend")
  val route: String   = "/platform-test-example-frontend"

  def startPage: HttpRequestBuilder =
    http("Start Page")
      .get(s"$baseUrl$route/")
      .check(status.in(200, 303))

  def whatPetLookingForForm: HttpRequestBuilder =
    http("What Pet Looking For Form")
      .get(s"$baseUrl$route/whatPetLookingFor")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitWhatPetLookingFor: HttpRequestBuilder =
    http("Submit What Pet Looking For")
      .post(s"$baseUrl$route/whatPetLookingFor")
      .formParam("value", "#{petType}")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/willPetBeAroundChildren"))

  def willPetBeAroundChildren: HttpRequestBuilder =
    http("Will Pet Be Around Children")
      .get(s"$baseUrl$route/willPetBeAroundChildren")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitWillPetBeAroundChildren: HttpRequestBuilder =
    http("Submit Will Pet Be Around Children")
      .post(s"$baseUrl$route/willPetBeAroundChildren")
      .formParam("value", "#{aroundChildren}")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/whenWantPetFrom"))

  def whenWantPetFrom: HttpRequestBuilder =
    http("When Want Pet From")
      .get(s"$baseUrl$route/whenWantPetFrom")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitWhenWantPetFrom: HttpRequestBuilder =
    http("Submit When Want Pet From")
      .post(s"$baseUrl$route/whenWantPetFrom")
      .formParam("value.day", "#{fromDay}")
      .formParam("value.month", "#{fromMonth}")
      .formParam("value.year", "#{fromYear}")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/whenWantPetUntil"))

  def whenWantPetUntil: HttpRequestBuilder =
    http("When Want Pet Until")
      .get(s"$baseUrl$route/whenWantPetUntil")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitWhenWantPetUntil: HttpRequestBuilder =
    http("Submit When Want Pet Until")
      .post(s"$baseUrl$route/whenWantPetUntil")
      .formParam("value.day", "#{untilDay}")
      .formParam("value.month", "#{untilMonth}")
      .formParam("value.year", "#{untilYear}")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-your-answers"))

  def checkYourAnswers: HttpRequestBuilder =
    http("Check Your Answers")
      .get(s"$baseUrl$route/check-your-answers")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitCheckYourAnswers: HttpRequestBuilder =
    http("Submit Check Your Answers")
      .post(s"$baseUrl$route/check-your-answers")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/payYourDeposit"))

  def payYourDeposit: HttpRequestBuilder =
    http("Pay Your Deposit")
      .get(s"$baseUrl$route/payYourDeposit")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def submitPayYourDeposit: HttpRequestBuilder =
    http("Submit Pay Your Deposit")
      .post(s"$baseUrl$route/payYourDeposit")
      .formParam("AccountName", "#{accountName}")
      .formParam("SortCode", "#{sortCode}")
      .formParam("AccountNumber", "#{accountNumber}")
      .formParam("RollNumber", "#{buildingSocietyRoll}")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/confirmation"))

  def confirmation: HttpRequestBuilder =
    http("Confirmation")
      .get(s"$baseUrl$route/confirmation")
      .check(status.is(200))

}
