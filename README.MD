# Mapstructor

## Iteration 1

* output = Lombok.builder().build()

## Iteration 2

* output.b = Lombok.builder().build()
* output.c = "abc"

## Iteration 3

* output.b.ghj = "abc"
* output.b.snickers = "abc"
* output.b.mars = Lombok.builder().build()
* output.c = "abc"

## Iteration 4

* output.b.ghj = "abc"
* output.b.snickers = "abc"
* output.b.mars.tzu = "abc"
* output.b.mars.zz = variable
* output.b.mars.ll = methodCall()
* output.c = "abc"

## Iteration 5

We are at the end

## Building first iteration of the mapper

    @Mapping(target="b.ghj",source = "abc")
    @Mapping(target="b.snickers,source = "abc")
    @Mapping(target="b.mars.tzu,source = "abc")
    @Mapping(target="b.mars.zz ,source = variable)
    @Mapping(target="b.mars.ll,source = methodCall())
    @Mapping(target="c,source = "abc")
    Output mapOutput(Object b, Object c);

## Building second iteration of the mapper


    @Mapping(target="c",source = "abc")
    @Mapping(target="b",source = "b")
    Output mapOutput(Object b, Object c);

    @Mapping(target="ghj",source = "abc")
    @Mapping(target="snickers,source = "abc")
    @Mapping(target="mars.tzu,source = "abc")
    @Mapping(target="mars.zz ,source = variable)
    @Mapping(target="mars.ll,source = methodCall())
    B mapB()

    @Mapping(target="c,source = "abc")
    C mapC()

## Building third iteration of the mapper

    @Mapping(target="c", source = "abc")
    @Mapping(target="b", source = "b")
    Output mapOutput(Object b, Object c);

    @Mapping(target="ghj", source = "abc")
    @Mapping(target="snickers, source = "abc")
    B mapB();

    @Mapping(target="tzu", source = "abc")
    @Mapping(target="zz", source = variable)
    @Mapping(target="ll", source = methodCall())
    Mars mapMars();

    @Mapping(target="c,source = "abc")
    C mapC()