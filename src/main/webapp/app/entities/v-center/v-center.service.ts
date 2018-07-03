import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVCenter } from 'app/shared/model/v-center.model';

type EntityResponseType = HttpResponse<IVCenter>;
type EntityArrayResponseType = HttpResponse<IVCenter[]>;

@Injectable({ providedIn: 'root' })
export class VCenterService {
    private resourceUrl = SERVER_API_URL + 'api/v-centers';

    constructor(private http: HttpClient) {}

    create(vCenter: IVCenter): Observable<EntityResponseType> {
        return this.http.post<IVCenter>(this.resourceUrl, vCenter, { observe: 'response' });
    }

    update(vCenter: IVCenter): Observable<EntityResponseType> {
        return this.http.put<IVCenter>(this.resourceUrl, vCenter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVCenter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
