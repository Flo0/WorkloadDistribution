# WorkloadDistribution

This repository provides extensiv example implementations for distributed workloads in Spigot.
More can be read in the forum thread:
https://www.spigotmc.org/threads/guide-on-workload-distribution-or-how-to-handle-heavy-splittable-tasks.409003/

Order for the packages should be
> simple
> looped
> selflimiting
> evendistribution

## From an expensive single blocking operation
![](https://github.com/Flo0/WorkloadDistribution/blob/master/readmesrc/instant_fill.gif)


## To a distributed async workload
![](https://github.com/Flo0/WorkloadDistribution/blob/master/readmesrc/dist_fill.gif)
