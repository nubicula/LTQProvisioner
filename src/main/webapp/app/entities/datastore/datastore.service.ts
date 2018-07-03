import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDatastore } from 'app/shared/model/datastore.model';

type EntityResponseType = HttpResponse<IDatastore>;
type EntityArrayResponseType = HttpResponse<IDatastore[]>;

@Injectable({ providedIn: 'root' })
export class DatastoreService {
    private resourceUrl = SERVER_API_URL + 'api/datastores';

    constructor(private http: HttpClient) {}

    create(datastore: IDatastore): Observable<EntityResponseType> {
        return this.http.post<IDatastore>(this.resourceUrl, datastore, { observe: 'response' });
    }

    update(datastore: IDatastore): Observable<EntityResponseType> {
        return this.http.put<IDatastore>(this.resourceUrl, datastore, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDatastore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDatastore[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
