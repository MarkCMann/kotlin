#include <stdio.h>
#include <signal.h>

__attribute__((visibility("hidden"))) constexpr int signalsToCover[] = {
  SIGILL, SIGFPE, SIGSEGV, SIGBUS, SIGUSR1, SIGUSR2
};

__attribute__((visibility("protected"))) extern "C" int mySigaction(int sig, const struct sigaction *act, struct sigaction * oact) {
  for (int i = 0; i < sizeof(signalsToCover)/sizeof(signalsToCover[0]); i++) {
    if (sig == signalsToCover[i]) return 0;
  }   //
  return sigaction(sig, act, oact);
}