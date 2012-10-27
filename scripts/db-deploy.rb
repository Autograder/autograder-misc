require 'sinatra'

use Rack::Auth::Basic, "Restricted Area" do |username, password|
  [username, password] == ['admin', 'c0ff33']
end

get '/latest' do
  send_file('/var/backups/latest-backup/backup.gz', :disposition => 'attachment', :filename => 'backup.gz')
end
