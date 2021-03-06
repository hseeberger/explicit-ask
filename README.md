# ExplicitAsk #

This is a very little library providing a workaround for an issue with 
[PatternCS.ask](https://github.com/akka/akka/issues/24587). Only needed until the issue is fixed
in Akka.

## Dependency configuration

- Group ID: rocks.heikoseeberger
- Artifact ID: explicit-ask 
- Version: 1.0.0

## Usage

Here's a snippet from the tests which hopefully is self explanatory:

``` java
final String expected = "hello";

final ActorRef echo = system.actorOf(Props.create(Echo.class));
final CompletionStage<String> response = ExplicitAsk
        .explicitAsk(
                echo,
                replyTo -> new Echo.Message(expected, replyTo),
                Timeout.apply(1, SECONDS))
        .thenApply(o -> (String)o);

final String actual = response.toCompletableFuture().get(1, SECONDS);
assertEquals(expected, actual);
```

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with
any pull requests, please state that the contribution is your original work and that you license
the work to the project under the project's open source license. Whether or not you state this
explicitly, by submitting any copyrighted material via pull request, email, or other means you
agree to license the material under the project's open source license and warrant that you have the
legal authority to do so.

## License ##

This code is open source software licensed under the
[Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0) license.
