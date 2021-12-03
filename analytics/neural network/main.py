from datetime import datetime
import json
import os

from flask import Flask, render_template, redirect, url_for, request, jsonify
from flask_session import Session
from werkzeug.utils import secure_filename
from services.stitcher import Stitcher

app = Flask('Predict Service')

UPLOAD_FOLDER = 'uploads/'
ALLOWED_EXTENSIONS = set(['jpg', 'png'])

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def _allowed_file(filename: str) -> bool:
    """
    Check is file allowed to upload
    """
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def _has_no_empty_params(rule) -> bool:
    defaults = rule.defaults if rule.defaults is not None else ()
    arguments = rule.arguments if rule.arguments is not None else ()
    return len(defaults) >= len(arguments)

def _get_endpoints() -> list:
    """
    Get list of endpoints
    """
    endpoints = []
    for rule in app.url_map.iter_rules():
        if "GET" in rule.methods and _has_no_empty_params(rule):
            url = url_for(rule.endpoint, **(rule.defaults or {}))
            endpoints.append((url, [*rule.methods]))
    return endpoints

@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html'), 404

@app.route('/help')
def helper():
    endpoints = _get_endpoints()
    return render_template('help.html', endpoints=endpoints)

@app.route('/help/json')
def helper_json():
    endpoints = _get_endpoints()
    return json.loads(json.dumps({"endpoints": endpoints}))

@app.route('/stitch')
def stitch():
    try:
        Stitcher().run()
        return json.loads('{"result": "ok"}')
    except:
        return json.loads('{"result": "fail"}')

@app.route('/predict/<int:pid>.jpg/stats', methods=['GET', 'POST'])
def predict_stats():
    pass

@app.route('/predict', methods=['GET', 'POST'])
def predict():
    path = '/home/serveriot/oil-api/img/'
    req = request.get_json(silent=True)
    print(req)
    issueId = req['issueId']
    images = req['img']
    # return json.loads('{"issueId": "issueId"}')


    response = {
        "issueId": issueId,
        "isSatelline": "",
        "img": []
    }

    # TODO: Resize image

    # Start predict
    import search.predictions

    for img in images:
        # search.predictions.run(os.path.join(path, img))
        response['img'].append({"originalImg": img,
                                "predictImg": 'mask_'+img,
                                "metricts": [
                                    {
                                        "key": "value"
                                    }
                                ]})

    # Write res in json


    return response
    # return json.loads(json.dumps({'filepath': path,
    #                               'result_mask': path[:-4]+'_mask.png'}))
    return '''
    <h1>Upload new file for predict</h1>
    <form method="post" enctype="multipart/form-data">
      <input type="file" name="upload_file">
      <input type="submit">
    </form>
    '''

if __name__ == '__main__':
    app.secret_key = 'D0No7U$3ThiSK3y1NPrOduC710Nc0de' # Hackathon style, kek. Not for production
    app.config['SESSION_TYPE'] = 'filesystem'

    sess = Session()
    sess.init_app(app)
    app.run()
