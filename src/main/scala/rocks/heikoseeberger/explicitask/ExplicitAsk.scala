/*
 * Copyright 2018 Heiko Seeberger
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

package rocks.heikoseeberger.explicitask

import akka.actor.ActorRef
import akka.pattern.ExplicitAskSupport
import akka.util.Timeout
import java.util.concurrent.CompletionStage
import scala.compat.java8.FutureConverters.FutureOps

object ExplicitAsk {

  private object Impl extends ExplicitAskSupport {
    def explicitAsk(actor: ActorRef,
                    messageFactory: ActorRef => AnyRef,
                    timeout: Timeout): CompletionStage[AnyRef] =
      ask(actor, messageFactory)(timeout).toJava.asInstanceOf[CompletionStage[AnyRef]]
  }

  /**
    * Ask pattern allowing for an explicit `replyTo` as part of the message.
    *
    * @param actor          the actor to be asked
    * @param messageFactory function taking an actor ref and returning the message to be sent
    * @param timeout        the timeout for the response before failing the returned completion stage
    * @return
    */
  def explicitAsk(actor: ActorRef,
                  messageFactory: ActorRef => AnyRef,
                  timeout: Timeout): CompletionStage[AnyRef] =
    Impl.explicitAsk(actor, messageFactory, timeout)
}
