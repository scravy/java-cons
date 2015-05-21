java-either
===========

Do you know [https://hackage.haskell.org/package/base-4.7.0.0/docs/Data-Either.html](Data.Either)?
So this is the crippled Java version of it.

It's on Maven Central
---------------------

    <dependency>
      <groupId>de.scravy</groupId>
	  <artifactId>either</artifactId>
	  <version>1.0.0</version>
    </dependency>

It's easy
---------

    final Either<String, Exception> value = Either.left("Hello World!");
    if (value.isLeft()) {
        System.out.println(value.getLeft());
    }

-or-

    final Either<String, Exception> value = Either.left("Hello World!");
    if (value instanceof Either.Left) {
        System.out.println(value.getLeft());
    }

It's free
---------

MIT License.
