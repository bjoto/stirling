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

import java.nio.ByteBuffer
import stirling.io.{ByteBuffers, ByteString}

sealed trait Message

sealed trait MessageType {
  def parse(buffer: ByteBuffer): Message
}

/*
 * Section 7.7.1
 */

case class Time(
  seconds: Int
) extends Message

object Time extends MessageType {
  def parse(buffer: ByteBuffer) = Time(
    seconds = buffer.getInt(4)
  )
}

/*
 * Section 7.7.2
 */

case class SystemEvent(
  nanosecond: Int,
  eventCode:  Byte
) extends Message

object SystemEvent extends MessageType {
  def parse(buffer: ByteBuffer) = SystemEvent(
    nanosecond = buffer.getInt(4),
    eventCode  = buffer.get()
  )
}

/*
 * Section 7.7.3
 */

case class SymbolDirectory(
  nanosecond:                      Int,
  instrumentID:                    Int,
  reserved1:                       Short,
  symbolStatus:                    Byte,
  isin:                            ByteString,
  priceBandTolerance:              Int,
  dynamicCircuitBreakerTolerances: Int,
  staticCircuitBreakerTolerances:  Int,
  segment:                         ByteString,
  reserved2:                       ByteString,
  currency:                        ByteString,
  reserved3:                       ByteString
) extends Message

object SymbolDirectory extends MessageType {
  def parse(buffer: ByteBuffer) = SymbolDirectory(
    nanosecond                      = buffer.getInt(4),
    instrumentID                    = buffer.getInt(4),
    reserved1                       = buffer.getShort(2),
    symbolStatus                    = buffer.get(),
    isin                            = ByteBuffers.slice(buffer, 11, 12),
    priceBandTolerance              = buffer.getInt(4),
    dynamicCircuitBreakerTolerances = buffer.getInt(4),
    staticCircuitBreakerTolerances  = buffer.getInt(4),
    segment                         = ByteBuffers.slice(buffer, 35,  6),
    reserved2                       = ByteBuffers.slice(buffer, 41,  6),
    currency                        = ByteBuffers.slice(buffer, 47,  3),
    reserved3                       = ByteBuffers.slice(buffer, 50, 40)
  )
}

/*
 * Section 7.7.4
 */

case class SymbolStatus(
  nanosecond:          Int,
  instrumentID:        Int,
  reserved1:           ByteString,
  tradingStatus:       Byte,
  reserved2:           ByteString,
  haltReason:          ByteString,
  sessionChangeReason: Byte,
  newEndTime:          Long,
  subBook:             Byte 
) extends Message

object SymbolStatus extends MessageType {
  def parse(buffer: ByteBuffer) = SymbolStatus(
    nanosecond          = buffer.getInt(4),
    instrumentID        = buffer.getInt(4),
    reserved1           = ByteBuffers.slice(buffer, 8, 2),
    tradingStatus       = buffer.get(),
    reserved2           = ByteBuffers.slice(buffer, 11, 1),
    haltReason          = ByteBuffers.slice(buffer, 12, 4),
    sessionChangeReason = buffer.get(),
    newEndTime          = buffer.getLong(8),
    subBook             = buffer.get()
  )
}
