ns         %     Task name
23600220200  026%  btree-1
22838178400  025%  btree-2
21494174300  024%  hash1
21801614700  024%  hash-2


-> hash_total / btree_total = 0.932327347739334





val btree = 23600220200 + 22838178400
val hash = 21494174300 + 21801614700

val performance = hash.toDouble() / btree.toDouble()

println(performance)


#One Table in DB

##B-Tree

running time = 176046606400 ns
ns         %     Task name
34960325000  020%  run-0
35325390900  020%  run-1
35611709300  020%  run-2
34932040200  020%  run-3
35217141000  020%  run-4

calls,total_time,min_time,max_time,mean_time,stddev_time,rows,shared_blks_hit,shared_blks_read
500000,27061.656500000634,0.0166,8.2693,0.05412331299999955,0.04725536184735177,503780,1911467,592313


##Hash Index

running time = 134752917900 ns
ns         %     Task name
27607502600  020%  run-0
26916700300  020%  run-1
26739594600  020%  run-2
26726184100  020%  run-3
26762936300  020%  run-4

calls,total_time,min_time,max_time,mean_time,stddev_time,rows,shared_blks_hit,shared_blks_read
500000,14654.551800000687,0.0062,1.4707999999999999,0.02930910360000035,0.018290204504172992,503780,432334
