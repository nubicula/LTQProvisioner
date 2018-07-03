import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVcenter } from 'app/shared/model/vcenter.model';

type EntityResponseType = HttpResponse<IVcenter>;
type EntityArrayResponseType = HttpResponse<IVcenter[]>;

@Injectable({ providedIn: 'root' })
export class VcenterService {
    private resourceUrl = SERVER_API_URL + 'api/vcenters';

    constructor(private http: HttpClient) {}

    create(vcenter: IVcenter): Observable<EntityResponseType> {
        return this.http.post<IVcenter>(this.resourceUrl, vcenter, { observe: 'response' });
    }

    update(vcenter: IVcenter): Observable<EntityResponseType> {
        return this.http.put<IVcenter>(this.resourceUrl, vcenter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVcenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVcenter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
