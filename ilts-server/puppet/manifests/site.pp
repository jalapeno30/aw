exec{'apt-get update': 
  command => '/usr/bin/apt-get update'
}

class {'java': 
  distribution => 'jdk',
  require => Exec['apt-get update'],
}

class { '::mysql::server':
  root_password           => '',
  remove_default_accounts => true,
}

mysql::db { 'lottery':
  user     => 'lottery',
  password => 'lottery',
  host     => 'localhost',
  sql      => '/vagrant/puppet/files/dump.sql',
  import_timeout => 900,
}

include stdlib 
include '::mysql::server'
include java
