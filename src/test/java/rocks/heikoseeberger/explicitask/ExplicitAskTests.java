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

package rocks.heikoseeberger.explicitask;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import java.util.concurrent.CompletionStage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public final class ExplicitAskTests {

    public static final class Echo extends AbstractActor {

        public static final class Message {

            public Message(final String text, ActorRef replyTo) {
                this.text = text;
                this.replyTo = replyTo;
            }

            public final String text;
            public final ActorRef replyTo;
        }

        public Receive createReceive() {
            return receiveBuilder()
                    .match(Message.class, message -> message.replyTo.tell(message.text, getSelf()))
                    .build();
        }
    }

    private static ActorSystem system;

    @BeforeClass
    public static void beforeClass() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void afterClass() {
        system.terminate();
    }

    @Test
    public void testExplicitAsk() throws Exception {
        final String expected = "hello";

        final ActorRef echo = system.actorOf(Props.create(Echo.class));
        final CompletionStage<String> response = ExplicitAsk
                .explicitAsk(
                        echo,
                        replyTo -> new Echo.Message(expected, replyTo),
                        Timeout.apply(1, SECONDS))
                .thenApply(Object::toString);

        final String actual = response.toCompletableFuture().get(1, SECONDS);
        assertEquals(expected, actual);
    }
}
