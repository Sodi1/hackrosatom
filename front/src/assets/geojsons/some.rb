require 'json'
require 'httparty'
require 'byebug'
json_string = File.open('mining.json').read
data = JSON.parse(json_string)
available_icons = ['https://energybase.ru/images/yandex-map-icons/66x66-filled-shadow/gas.png']
data['features'].reject { |f| available_icons.include?(f['options']['iconImageHref']) }.map do |npc|
  body = {
    fullName: npc['properties']['clusterCaption'] || '',
    shortName: npc['properties']['clusterCaption'] || '',
    lat: npc['geometry']['coordinates'][0],
    lon: npc['geometry']['coordinates'][1],
    balloonContentHeader: npc['properties']['BalloonContentHeader'] || '',
    balloonContentBody: npc['properties']['BalloonContentBody'] || '',
    BalloonContentFooter: npc['properties']['BalloonContentFooter'] || '',
    clusterCaption: npc['properties']['clusterCaption'] || '',
    "plantKind": "MINING"
  }
  r = HTTParty.post('https://oil-api.kovalev.team/api/plant',
                    headers: { 'Content-Type' => 'application/json' },
                    body: body.to_json)
  p r.code
end
