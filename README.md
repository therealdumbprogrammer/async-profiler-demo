# async-profiler demos

Small Java 25 programs for exploring CPU sampling, wall-clock sampling, lock contention, and allocation profiling with async-profiler 4.6

```bash
java DemoCPU
```

`DemoCPU` continuously calculates primes and square roots on a thread named `calculator`, alongside a sleeping `waiting-thread`. `LockDemo` runs two threads that contend on a deliberately held monitor. `AllocationDemo` repeatedly allocates byte arrays.

Stop a demo with Ctrl-C. In the commands below, replace `<PID>` with the printed process ID. Run each capture while its matching demo is active.

## CPU mode

With `DemoCPU` running, capture CPU samples as an HTML flame graph:

```bash
asprof -e cpu -d 15 -t -f cpu.html <PID>
open cpu.html
```

Useful variants:

```bash
# Sample every 5 ms instead of the default interval
asprof -e cpu -i 5ms -d 15 -t -f cpu-5ms.html <PID>

# Record only samples attributed to the named thread
asprof -e cpu -d 15 -t --ithread calculator -f cpu-calculator.html <PID>

# Save a JFR recording for inspection in JDK Mission Control
asprof -e cpu -d 15 -f cpu.jfr <PID>
```

## Wall-clock mode

With `DemoCPU` running, wall-clock sampling includes time when threads are waiting or sleeping. The program's `waiting-thread` sleeps between samples.

```bash
asprof -e wall -i 10ms -d 15 -t -f wall.html <PID>
open wall.html
```

Filter wall-clock samples by thread name; repeat `--ithread` to include more than one thread:

```bash
asprof -e wall -i 10ms -d 15 -t \
  --ithread calculator --ithread waiting-thread \
  -f wall-selected-threads.html <PID>
```

## Lock mode

Run `LockDemo`, then capture lock contention (the threshold is 5 ms):

```bash
asprof -e lock --lock 5ms -d 15 -t -f lock.html <PID>
open lock.html
```

## Allocation mode

Run `AllocationDemo`, then capture allocation samples:

```bash
asprof -e alloc -d 15 -t -f alloc.html <PID>
open alloc.html
```

Allocation profiles show sampled allocation activity, not retained heap size.
