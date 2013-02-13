/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stirling.oslo.itch20

/*
 * Section 7.7.2
 */

object SystemEventEventCode {
  val EndOfDay   = 'C'.toByte
  val StartOfDay = 'O'.toByte
}

/*
 * Section 7.7.3
 */

object SystemEventSymbolStatus {
  val Halted    = 'H'.toByte
  val Suspended = 'S'.toByte
  val Inactive  = 'a'.toByte
}

/*
 * Section 7.7.4
 */

object SymbolStatusTradingStatus {
  val Halt                    = 'H'.toByte
  val Regular                 = 'T'.toByte
  val OpeningAuctionCall      = 'a'.toByte
  val PostClose               = 'b'.toByte
  val MarketClosed            = 'c'.toByte
  val ClosingAuctionCall      = 'd'.toByte
  val ReOpeningAuctionCall    = 'e'.toByte
  val NoActiveSession         = 'w'.toByte
  val EndOfPostClose          = 'x'.toByte
  val PreTrading              = 'y'.toByte
  val ClosingPricePublication = 'z'.toByte 
}

object SymbolStatusSessionChangeReason {
  val ScheduledTransition           = '0'.toByte
  val ExtendedByMarketSurveillance  = '1'.toByte
  val ShortenedByMarketSurveillance = '2'.toByte
  val MarketOrderImbalance          = '3'.toByte
  val PriceOutsideRange             = '4'.toByte
  val CircuitBreakerTripped         = '5'.toByte
}

object SymbolStatusSubBook {
  val Regular = '1'.toByte
  val OffBook = '2'.toByte
}
