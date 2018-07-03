import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStorageArray } from 'app/shared/model/storage-array.model';

type EntityResponseType = HttpResponse<IStorageArray>;
type EntityArrayResponseType = HttpResponse<IStorageArray[]>;

@Injectable({ providedIn: 'root' })
export class StorageArrayService {
    private resourceUrl = SERVER_API_URL + 'api/storage-arrays';

    constructor(private http: HttpClient) {}

    create(storageArray: IStorageArray): Observable<EntityResponseType> {
        return this.http.post<IStorageArray>(this.resourceUrl, storageArray, { observe: 'response' });
    }

    update(storageArray: IStorageArray): Observable<EntityResponseType> {
        return this.http.put<IStorageArray>(this.resourceUrl, storageArray, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStorageArray>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStorageArray[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
